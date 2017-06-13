package domain.validators;

import domain.Student;

public class StudentValidator implements Validator<Student> {

	@Override
	public void validate(Student e) throws ValidatorException {
		String errMsg="";
		if (e.getId() == null || "".equals(e.getId()))
			errMsg+="Id error ";
		if (e.getFirstName() == null || "".equals(e.getFirstName()))
			errMsg+="first name error ";
		if (e.getLastName() == null || "".equals(e.getLastName()))
			errMsg+="last name error ";
		if (e.getEmail() == null || "".equals(e.getEmail()))
			errMsg+="email error error ";
		if (errMsg!="")
			throw new ValidatorException(errMsg);
	}

}
