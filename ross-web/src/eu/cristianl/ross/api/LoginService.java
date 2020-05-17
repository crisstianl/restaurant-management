package eu.cristianl.ross.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import eu.cristianl.ross.dal.UserSessionDal;
import eu.cristianl.ross.dal.WorkerDal;
import eu.cristianl.ross.dal.utils.UserSessionUtils;
import eu.cristianl.ross.entities.Employee;
import eu.cristianl.ross.entities.UserSession;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

@Path("/login")
public class LoginService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(200).entity("Hello").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postLogin(@FormParam("name") String userName, @FormParam("password") String password,
			@Context HttpServletRequest request) {
		try {
			if (Utils.isEmptyOrBlank(userName) && Utils.isEmptyOrBlank(password)) {
				return Response.status(400).entity("The username or password field is null").build();
			}

			String clientIp = Utils.getClientIp(request);
			if (Utils.isEmptyOrBlank(clientIp)) {
				return Response.status(500).build();
			}

			Employee employee = WorkerDal.I().getEmployee(userName, password);
			if (employee == null) {
				return Response.status(404).entity("The username or password field is invalid").build();
			}

			return getUserSession(employee, clientIp);
		} catch (Exception e) {
			AppLogger.error(e, "Failed to query Employee table");
			return Response.status(500).build();
		}
	}

	/** Check for active user sessions, or create a new one */
	private Response getUserSession(Employee employee, String clientIp) throws Exception {
		// Check for active user sessions, only one
		UserSession session = UserSessionDal.I().getActiveSession(employee.getId());
		if (session == null) {
			session = createUserSession(employee, clientIp);
		}
		return serialize(session);
	}

	private static UserSession createUserSession(Employee employee, String clientIp) throws Exception {
		final String sessionId = UserSessionUtils.generateSessionId(employee.getId().toString(),
				employee.getUsername());
		final Date startTime = new Date(); // now
		final Date endTime = UserSessionUtils.generateSessionEndTime(startTime); // 8 hours

		UserSession session = new UserSession(sessionId, employee, startTime, endTime, clientIp, null);
		UserSessionDal.I().createSession(session);
		return session;
	}

	private static Response serialize(final UserSession session) throws Exception {
		final JSONObject json = new JSONObject();
		json.put("session_id", session.getSessionId());
		json.put("session_time", session.getEndTime().getTime());
		json.put("employee_id", session.getEmployee().getId());
		json.put("employee_username", session.getEmployee().getUsername());
		json.put("employee_name", session.getEmployee().getName());

		return Response.status(200).entity(json.toString()).build();
	}
}
