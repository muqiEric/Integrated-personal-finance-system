package com.example.financeReportSystem.controller;

import com.example.financeReportSystem.dto.FamilyMemberRequest;
import com.example.financeReportSystem.dto.FamilyMemberResponse;
import com.example.financeReportSystem.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    // 获取家庭成员列表
    @GetMapping("/{userId}")
    public ResponseEntity<List<FamilyMemberResponse>> getFamilyMembers(@PathVariable Long userId) {
        List<FamilyMemberResponse> familyMembers = familyMemberService.getFamilyMembers(userId);
        return ResponseEntity.ok(familyMembers);
    }

    // 添加家庭成员
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addFamilyMember(@PathVariable Long userId, @RequestBody FamilyMemberRequest request) {
        familyMemberService.addFamilyMember(userId, request);
        return ResponseEntity.ok("家庭成员添加成功");
    }

    // 删除家庭成员
    @DeleteMapping("/{userId}/remove/{memberId}")
    public ResponseEntity<String> removeFamilyMember(@PathVariable Long userId, @PathVariable Long memberId) {
        familyMemberService.removeFamilyMember(userId, memberId);
        return ResponseEntity.ok("家庭成员删除成功");
    }
}
