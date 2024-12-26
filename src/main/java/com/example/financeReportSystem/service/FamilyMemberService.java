package com.example.financeReportSystem.service;

import com.example.financeReportSystem.dto.FamilyMemberRequest;
import com.example.financeReportSystem.dto.FamilyMemberResponse;
import com.example.financeReportSystem.model.FamilyMember;
import com.example.financeReportSystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    // 获取家庭成员列表
    public List<FamilyMemberResponse> getFamilyMembers(Long userId) {
        List<FamilyMember> members = familyMemberRepository.findByUserId(userId);
        return members.stream().map(member -> new FamilyMemberResponse(member.getId(), member.getName())).collect(Collectors.toList());
    }

    // 添加家庭成员
    public void addFamilyMember(Long userId, FamilyMemberRequest request) {
        FamilyMember existingMember = familyMemberRepository.findByAccountAndPassword(request.getAccount(), request.getPassword());
        if (existingMember == null) {
            throw new RuntimeException("账号或密码错误！");
        }
        if (familyMemberRepository.existsByUserIdAndMemberId(userId, existingMember.getId())) {
            throw new RuntimeException("该家庭成员已添加！");
        }
        familyMemberRepository.addMemberToUser(userId, existingMember.getId());
    }

    // 删除家庭成员
    public void removeFamilyMember(Long userId, Long memberId) {
        familyMemberRepository.removeMemberFromUser(userId, memberId);
    }
}
