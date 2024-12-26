package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findByUserId(Long userId);

    @Query("SELECT f FROM FamilyMember f WHERE f.account = ?1 AND f.password = ?2")
    FamilyMember findByAccountAndPassword(String account, String password);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM UserFamilyMapping ufm WHERE ufm.userId = ?1 AND ufm.memberId = ?2")
    boolean existsByUserIdAndMemberId(Long userId, Long memberId);

    @Query(value = "INSERT INTO user_family_mapping (user_id, member_id) VALUES (?1, ?2)", nativeQuery = true)
    void addMemberToUser(Long userId, Long memberId);
                                                                              
    @Query(value = "DELETE FROM user_family_mapping WHERE user_id = ?1 AND member_id = ?2", nativeQuery = true)
    void removeMemberFromUser(Long userId, Long memberId);
}
