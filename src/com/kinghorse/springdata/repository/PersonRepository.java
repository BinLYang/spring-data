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
	
	//	ʹ��ռλ��
	@Query("SELECT p FROM Person p WHERE p.lastName =?1 AND p.email = ?2")
	public List<Person> testQueryAnnotationParam1(String name ,String email);
	
	//	ʹ����������
	@Query("SELECT p FROM Person p WHERE p.lastName =:name AND p.email = :email")
	public List<Person> testQueryAnnotationParam2(@Param("name") String name ,@Param("email")String email);
	
	//	ע��like��ʹ�÷�ʽ :��jpql����в���ǰ����% ������в�����Ҫд %���෴ ����Ҫд��
	@Query("SELECT p FROM Person p WHERE p.lastName like %?1% AND p.email like %?2%")
	public List<Person> testQueryAnnotationLikeParam(String name ,String email);
	
	//ʹ��ԭ����sql��ѯ  nativeQuery=true
	@Query(value="select count(id) from jpa_person", nativeQuery=true)
	long getTotalCount();
	
	//����ͨ���Զ����jpql ���update �� delete ��������ʹ��@Modifyingע���������
	//���ұ���ʹ��������Ҫ����service�㣬��service����������� ��ע�⣺jpql��֧��insert
	//Ĭ������£�SpringData��ÿ�������������񣬵�����ֻ���������ǲ�������޸Ĳ���
	@Modifying
	@Query("UPDATE Person p SET p.email = :email WHERE id = :id")
	public void updatePersonEmail(@Param("email") String email, @Param("id") Integer id);
}
