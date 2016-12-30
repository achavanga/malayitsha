package za.co.thinking.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import za.co.thinking.model.security.Person;
import za.co.thinking.model.security.User;
import za.co.thinking.security.service.IUserService;

/**
 * File Name : UserRestService.java Project Name : MainThinkify-web
 *
 * @since Dec 20, 2016, 10:56:36 AM
 * @author Abel Chavanga <achavanga@fnb.co.za>
 *
 */
@Path("/users")
@RequestScoped
@Stateful
public class UserRestService {
//    @Inject
//    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private IUserService userService;

    public UserRestService() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listAllUser() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public User finById(@PathParam("id") long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }
    /**
     * Creates a new member from the values provided. Performs validation, and
     * will return a JAX-RS response with either 200 ok, or with a map of
     * fields, and related errors.
     *
     * @param user
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {

        Response.ResponseBuilder builder = null;
        Person person = new Person();
        person.setName(user.getUserName());
        user.setPerson(person);

        try {
            // Validates member using bean validation
            validateMember(user);

            userService.seveUser(user);

            // Create an "ok" response
            builder = Response.ok().entity(user);
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * <p>
     * Validates the given Member variable and throws validation exceptions
     * based on the type of error. If the error is standard bean validation
     * errors then it will throw a ConstraintValidationException with the set of
     * the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing member with the same email is
     * registered it throws a regular validation exception so that it can be
     * interpreted separately.
     * </p>
     *
     * @param member Member to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If member with the same email already exists
     */
    private void validateMember(User user) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
//        if (emailAlreadyExists(user.getPerson().getEmailAddress())) {
//            throw new ValidationException("Unique Email Violation");
//        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation
     * fields, and their message. This can then be used by clients to show
     * violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
//        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a member with the same email address is already registered.
     * This is the only way to easily capture the "@UniqueConstraint(columnNames
     * = "email")" constraint from the Member class.
     *
     * @param email The email to check
     * @return True if the email already exists, and false otherwise
     */
    public boolean emailAlreadyExists(String email) {
        User member = null;
        try {
            member = userService.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return member != null;
    }
}
