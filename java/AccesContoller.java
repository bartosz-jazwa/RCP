import database.entity.Employee;

import java.util.Set;

public class AccesContoller {
    private Set<Employee> employees;

    public Employee login(String username, String password){



        PBKDF2Hasher hasher = new PBKDF2Hasher();
        String hashedPassword = hasher.hash(password.toCharArray());

        return employees.stream()
                .filter(employee -> employee.getCredentials().getUsername().equals(username))
                .filter(employee -> employee.getCredentials().getHashedPassword().equals(hashedPassword))
                .findAny()
                .get();



    }
}
