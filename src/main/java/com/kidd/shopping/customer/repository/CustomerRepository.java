package com.kidd.shopping.customer.repository;

import com.kidd.shopping.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
//    @Modifying
//    @Transactional
//    @Query("update Student s set s.avatarUrl = ?2 where s.id = ?1")
//    void updateAvatarUrl(String studentID, String downloadUrl);
//
//    @Modifying
//    @Transactional
//    @Query("update Student s set s.coverUrl = ?2 where s.id = ?1")
//    void updateCoverUrl(String studentID, String downloadUrl);
//
//    @Modifying
//    @Transactional
//    @Query("update Student s set s.identityCardFrontImageUrl = ?2, s.identityCardBackImageUrl = ?3 where s.id = ?1")
//    void updateIDCardUrl(String studentID, String downloadUrl1, String downloadUrl2);
//
//    @Modifying
//    @Transactional
//    @Query("update Student s set s.isLookingForHouse = ?2 where s.id = ?1")
//    void updateLookingforHouseState(String studentID, boolean state);
//
//    @Modifying
//    @Transactional
//    @Query(value = "update Student s set s.description = ?2 where s.id = ?1")
//    void updateStudentDescription(String studentID, String description);
////
////    @Query("select new com.ptit.edu.student_house.student.model.view.StudentHeaderProfile" +
////            "(s.user.id,s.email,s.avatarUrl,s.coverUrl,s.firstName,s.lastName,s.region.name) " +
////            "from Student s " +
////            "where s.id = ?1")
////    StudentHeaderProfile getStudentHeaderProfile(String studentID);
////
////    @Query("select new com.ptit.edu.student_house.student.model.view.StudentProfile(s,r.name) " +
////            "from Student s join s.region r " +
////            "where s.id = ?1")
////    StudentProfile getStudentProfile(String studentID);
//
//    @Query("select s.id from Student s join s.user u where u.username = ?1")
//    String getStudentID(String username);
//
//    @Query("select s.id from Student s join s.user u where u.id = ?1")
//    String getStudentIDByUserID(String userID);
//
//
//    @Query("select s.identityCardFrontImageUrl from Student s where s.id = ?1")
//    String getIdentityFrontImageUrk(String studentID);
//
//    @Query("select s.identityCardBackImageUrl from Student s where s.id = ?1")
//    String getIdentityBackImageUrk(String studentID);
//
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("update Student s set s.isProfileVerify = :state where s.id = :id")
//    void updateIsProfileVerify(@Param("state") boolean state, @Param("id") String id);

}
