import codefinity.model.Department;
import codefinity.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityIntegrationTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        final BootstrapServiceRegistryBuilder bootstrapRegistryBuilder = new BootstrapServiceRegistryBuilder();
        bootstrapRegistryBuilder.applyClassLoader(ClassLoaderService.class.getClassLoader());
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder(bootstrapRegistryBuilder.build())
                .configure("hibernate.cfg.xml")
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Department.class);
        metadataSources.addAnnotatedClass(Employee.class);

        MetadataImplementor metadata = (MetadataImplementor) metadataSources.buildMetadata();
        sessionFactory = metadata.buildSessionFactory();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        if (session.isOpen()) {
            session.close();
        }
        if (sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    @Test
    public void testConnection() {
        assertNotNull(sessionFactory, "Session factory is null, connection cannot be established.");
    }

    @Test
    public void testEmployeeEntityMapping() {
        Department department = new Department();
        department.setName("Development");
        department.setLocation("Main Office");
        session.persist(department);

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setPosition("Developer");
        employee.setSalary(75000);
        employee.setHireDate(new Date());
        employee.setDepartment(department);
        session.persist(employee);

        session.flush();
        session.clear();

        Employee retrievedEmployee = session.get(Employee.class, employee.getId());
        assertNotNull(retrievedEmployee, "Employee was not retrieved, mapping may be incorrect.");
        assertNotNull(retrievedEmployee.getDepartment(), "Department of the retrieved employee is null, association mapping may be incorrect.");
    }

    @Test
    public void testDepartmentEntityMapping() {
        Department department = new Department();
        department.setName("Human Resources");
        department.setLocation("HQ");
        session.persist(department);

        session.flush();
        session.clear();

        Department retrievedDepartment = session.get(Department.class, department.getId());
        assertNotNull(retrievedDepartment, "Department was not retrieved, mapping may be incorrect.");
    }

}
