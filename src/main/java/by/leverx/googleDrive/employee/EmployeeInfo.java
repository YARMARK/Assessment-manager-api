package by.leverx.googleDrive.employee;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "employee_info")
public class EmployeeInfo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_gen")
  @SequenceGenerator(name = "employee_gen", sequenceName = "employee_seq", allocationSize = 1)
  private Long employeeId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "last_assessment_folder")
  private String lastAssessmentFolder;

  @Column(name = "title")
  private String title;

  @Column(name = "team_name")
  private String teamName;

  @Column(name = "date_of_incoming")
  private LocalDate dateOfIncoming;

  @Column(name = "previous_assessment_date")
  private LocalDate previousAssessmentDate;

  @Column(name = "need_assessment")
  private Boolean needAssessment;
}