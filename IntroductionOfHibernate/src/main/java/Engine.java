import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable{

    private final EntityManager entityManager;
    private BufferedReader bufferReader;

    public Engine(EntityManager entityManager){
    this.entityManager = entityManager;
    this.bufferReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select exercise number: ");
        try {
            int exerciseNumber = Integer.parseInt(bufferReader.readLine());
            switch (exerciseNumber) {
                case 2:
                    solveProblemTwoChangeCasing();
                    break;
                case 3:
                    solveProblemThree();
                    break;
                case 4:
                    solveProblemFour();
                    break;
                case 5:
                    solveProblemFive();
                    break;
                case 6:
                    solveProblemSix();
                    break;
                case 7:
                    solveProblemSeven();
                    break;
                case 8:
                    solveProblemEight();
                    break;
                case 9:
                    solveProblemNine();
                    break;
                case 10:
                    solveProblemTen();
                    break;
                case 11:
                    solveProblemEleven();
                    break;
                case 12:
                    solveProblemTwelve();
                    break;
                case 13:
                    solveProblemThirteen();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void solveProblemSix() throws IOException {
        System.out.println("Enter employee's last name:");
        String lastName = bufferReader.readLine();

       List<Employee> employee = entityManager.createQuery("select e from Employee as e where e.lastName = :ln", Employee.class)
                .setParameter("ln", lastName)
                .getResultList();

       String addressName = "Vitoshka 15";
       Town town = entityManager.createQuery("select t from Town as t where t.name =:tn", Town.class).setParameter("tn","Sofia").getSingleResult();
       Address newAdres= createNewAddress(addressName, town);
       entityManager.getTransaction().begin();
        employee.get(0).setAddress(newAdres);
        entityManager.getTransaction().commit();
        System.out.println(employee.get(0).getAddress().getText());
    }

    private Address createNewAddress(String addrss, Town Town) {

        Address address = new Address();
        address.setText(addrss);
        address.setTown(Town);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void solveProblemSeven() {

        List<Address> resultList = entityManager.createQuery("select a from Address a order by a.employees.size desc", Address.class)
                .setMaxResults(10).getResultList();

        for (Address address : resultList) {
            System.out.printf("%s %s - %d%n", address.getText(), address.getTown().getName(),address.getEmployees().size());
        }
    }

    private void solveProblemEight() throws IOException {
        System.out.println("Enter employee's ID:");
        int id = Integer.parseInt(bufferReader.readLine());
        Employee employee = entityManager.find(Employee.class, id);
        if(employee==null){
            System.out.println(String.format("There is no employee with ID:%d! Try again.", id));
            return;
        }
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String jobTitle = employee.getJobTitle();
        System.out.println(String.format("%s %s - %s", firstName, lastName, jobTitle));

        Set<Project> projectSet = employee.getProjects();
        if(projectSet.size()==0){
            System.out.println(String.format("This employee has no projects"));
            return;
        }
        projectSet.stream().sorted((p1,p2)->p1.getName().compareTo(p2.getName()))
                .forEach(p-> System.out.println(String.format("\t%s", p.getName())));
    }

    private void solveProblemNine() {
        List<Project> projects = entityManager.createQuery("select p from Project as p order by p.startDate desc", Project.class)
                .setMaxResults(10).getResultList();
        projects.stream().sorted((p1, p2)-> p1.getName().compareToIgnoreCase(p2.getName()))
                .forEach(project -> {
                    String projectName = project.getName();
                    String projectDescription = project.getDescription();
                    LocalDateTime startDate = project.getStartDate();
                    LocalDateTime endDate = project.getEndDate();
                    System.out.println("Project name: " + projectName);
                    System.out.println("    " + "Project Description: " + projectDescription);
                    System.out.println("    " + "Project Start Date:" + startDate);
                    if (endDate != null) {
                        System.out.println("    " + "Project End Date:" + endDate);
                    } else {
                        System.out.println("    " + "Project End Date null");
                    }
                });
    }

    private void solveProblemTen() {
        entityManager.getTransaction().begin();
        int affected = entityManager.createQuery("update Employee as e set e.salary = e.salary*1.12" +
                " where e.department.id in :dep_ids").setParameter("dep_ids", Set.of(1,2,4,11)).executeUpdate();

        entityManager.getTransaction().commit();
        List<Employee> employeesWithIncreasedSalaries = new ArrayList<>();
        if (affected == 0) {
            System.out.println("No salaries were increased. Please enter valid departments ids");
//            throw new IllegalArgumentException("No salaries were increased. Input departments ID don't exist.");
        } else {
            employeesWithIncreasedSalaries =entityManager.createQuery("select e from Employee e where e.department.id in :dep_ids", Employee.class)
                    .setParameter("dep_ids", Set.of(1,2,4,11)).getResultList();
        }

        for (Employee employee : employeesWithIncreasedSalaries) {
            System.out.println(String.format("%s %s ($%.2f)", employee.getFirstName(), employee.getLastName(), employee.getSalary()));
        }
    }

    private void solveProblemEleven() throws IOException {
        System.out.println("Enter the pattern:");
        String inputPattern = bufferReader.readLine();
        String pattern = inputPattern + "%";

        List<Employee> employees = entityManager.createQuery("select e from Employee e" +
                " where e.firstName like :n",Employee.class).setParameter("n", pattern).getResultList();

        for (Employee employee : employees) {
            System.out.println(String.format("%s %s - %s - ($%.2f)",
                    employee.getFirstName(),employee.getLastName(), employee.getJobTitle(), employee.getSalary()));
        }
    }

    private void solveProblemThirteen() throws IOException {
        System.out.println("Enter town name:");
        String townName = bufferReader.readLine();
        Town town = entityManager
                .createQuery("select t from Town t where t.name = :tn", Town.class)
                .setParameter("tn", townName)
                .getSingleResult();

        int affected = removeAddressByTownId(town.getId());
        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.println(String.format("%d address in %s is deleted", affected, townName));

    }

    private int removeAddressByTownId(Integer id) {
        List<Address> addresses = entityManager
                .createQuery("select a from Address a where a.town.id = :p_id", Address.class)
                .setParameter("p_id", id)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

        return addresses.size();
    }

    private void solveProblemTwelve() {
        List<Object[]>  rows = entityManager
                .createNativeQuery("select d.name, max(e.salary) as max_salary " +
                        " from soft_uni.departments as d" +
                        " join soft_uni.employees as e" +
                        " on d.department_id = e.department_id" +
                        " group by d.name" +
                        " having max_salary not between 30000 and 70000")
                .getResultList();

        for (int i = 0; i < rows.size(); i++) {
            Object[] objects = rows.get(i);
            Object department = objects[0];
            String simpleName = department.getClass().getName().toString();
            Object salary = objects[1];
            System.out.println(department + " " + salary);
        }
    }



    private void solveProblemFive() {
        String departmentName = "Research and Development";
        List <Employee> resultList = entityManager.createQuery("select e from Employee as e" +
                " WHERE e.department.name=:d" +
                " ORDER BY e.salary, e.id", Employee.class).setParameter("d",departmentName).getResultList();

        for (Employee employee : resultList) {
            System.out.printf("%s %s %s - $%.2f%n", employee.getFirstName(), employee.getLastName(),employee.getDepartment().getName(),employee.getSalary());
        }
    }

    private void solveProblemFour() {
        BigDecimal money = new BigDecimal(50000);
        List <Employee> resultList = entityManager.createQuery("select e from Employee as e where e.salary>=:money", Employee.class)
                .setParameter("money",money ).getResultList();

        for (Employee employee : resultList) {
            System.out.println(employee.getFirstName());
        }
    }

    private void solveProblemThree() throws IOException {

        System.out.println("Enter employee's full name");
        String [] fullName = bufferReader.readLine().split("\\s+");

        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("select count(e) from Employee as e where e.firstName=:fn and e.lastName=:ln", Long.class)
                .setParameter("fn",firstName)
                .setParameter("ln", lastName).getSingleResult();
        if(singleResult>0){
            System.out.println("Yes");
        }else {
            System.out.println("No");
        }
    }

    private void solveProblemTwoChangeCasing() {
        entityManager.getTransaction().begin();
        Query from_town = entityManager.createQuery("Select t FROM Town t", Town.class);
        List <Town> resultList = from_town.getResultList();

        for (Town town : resultList) {
            String name = town.getName();
            if(name.length()<=5){
             String toUpper=   name.toUpperCase();
             town.setName(toUpper);
             entityManager.persist(town);
            }
        }
        entityManager.getTransaction().commit();

    }
}
