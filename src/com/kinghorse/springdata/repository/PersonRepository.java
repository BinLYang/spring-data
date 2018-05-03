package com.kinghorse.springdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import com.kinghorse.springdata.Person;

//@RepositoryDefinition(domainClass=PersonRepository.class, idClass=Integer.class)
public interface PersonRepository extends PagingAndSortingRepository<Person, Integer>,JpaSpecificationExecutor<Person> {
	//public interface PersonRepository extends Repository<Person, Integer> {

	public Person getByLastName(String name);
	
	@Query("SELECT P FROM Person p WHERE p.id = (SELECT max(p2.id) FROM Person p2)")
	public Person getMaxIdPerson();
	
	//	使用占位符
	@Query("SELECT p FROM Person p WHERE p.lastName =?1 AND p.email = ?2")
	public List<Person> testQueryAnnotationParam1(String name ,String email);
	
	//	使用命名参数
	@Query("SELECT p FROM Person p WHERE p.lastName =:name AND p.email = :email")
	public List<Person> testQueryAnnotationParam2(@Param("name") String name ,@Param("email")String email);
	
	//	注意like的使用方式 :若jpql语句中参数前后有% 则参数中不再需要写 %。相反 则需要写上
	@Query("SELECT p FROM Person p WHERE p.lastName like %?1% AND p.email like %?2%")
	public List<Person> testQueryAnnotationLikeParam(String name ,String email);
	
	//使用原生的sql查询  nativeQuery=true
	@Query(value="select count(id) from jpa_person", nativeQuery=true)
	long getTotalCount();
	
	//可以通过自定义的jpql 完成update 和 delete 操作必须使用@Modifying注解进行修饰
	//并且必须使用事务，需要定义service层，在service层上添加事务 。注意：jpql不支持insert
	//默认情况下，SpringData的每个方法上有事务，但都是只读事务，他们不能完成修改操作
	@Modifying
	@Query("UPDATE Person p SET p.email = :email WHERE id = :id")
	public void updatePersonEmail(@Param("email") String email, @Param("id") Integer id);
}
