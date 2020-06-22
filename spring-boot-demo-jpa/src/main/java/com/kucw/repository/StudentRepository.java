package com.kucw.repository;

import com.kucw.entity.Student;
import org.springframework.data.repository.CrudRepository;

//這裡就可以寫query的方法，Hibernate會自動幫我們生成對應的SQL，我們只要照著規則寫方法名就可以了
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findByAge(Integer age); //這裡實際上就會生成 SELECT * FROM student WHERE age = :age 的sql語法

}
