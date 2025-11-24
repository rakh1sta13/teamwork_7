package com.ramen.ordering.service;

import com.ramen.ordering.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    List<BranchDTO> getAllBranches();
    List<BranchDTO> getActiveBranches();
    BranchDTO getBranchById(Long id);
    BranchDTO createBranch(BranchDTO branchDTO);
    BranchDTO updateBranch(Long id, BranchDTO branchDTO);
    boolean deleteBranch(Long id);
}