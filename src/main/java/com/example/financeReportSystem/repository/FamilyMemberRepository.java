package com.example.financeReportSystem.repository;

import com.example.financeReportSystem.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    // 根据用户ID获取家庭成员列表（需要关联表 user_family_mapping）
    @Query(value = "SELECT fm.* FROM family_members fm " +
            "JOIN user_family_mapping ufm ON fm.id = ufm.member_id " +
            "WHERE ufm.user_id = ?1", nativeQuery = true)
    List<FamilyMember> findByUserId(Long userId);

    // 根据账号和密码查找家庭成员（支持登录验证）
    @Query(value = "SELECT * FROM family_members WHERE account = ?1 AND password = ?2", nativeQuery = true)
    FamilyMember findByAccountAndPassword(String account, String password);

    // 检查是否已存在关联
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM user_family_mapping WHERE user_id = ?1 AND member_id = ?2", nativeQuery = true)
    boolean existsByUserIdAndMemberId(Long userId, Long memberId);

    // 添加家庭成员到用户
    @Modifying
    @Query(value = "INSERT INTO user_family_mapping (user_id, member_id) VALUES (?1, ?2)", nativeQuery = true)
    void addMemberToUser(Long userId, Long memberId);

    // 从用户中移除家庭成员
    @Modifying
    @Query(value = "DELETE FROM user_family_mapping WHERE user_id = ?1 AND member_id = ?2", nativeQuery = true)
    void removeMemberFromUser(Long userId, Long memberId);
}
