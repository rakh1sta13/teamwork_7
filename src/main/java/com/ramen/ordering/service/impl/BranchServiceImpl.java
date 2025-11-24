package com.ramen.ordering.service.impl;

import com.ramen.ordering.dto.BranchDTO;
import com.ramen.ordering.entity.Branch;
import com.ramen.ordering.mapper.BranchMapper;
import com.ramen.ordering.repository.BranchRepository;
import com.ramen.ordering.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BranchDTO> getActiveBranches() {
        return branchRepository.findByIsActiveTrue()
                .stream()
                .map(branchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long id) {
        return branchRepository.findById(id)
                .map(branchMapper::toDTO)
                .orElse(null);
    }

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = branchMapper.toEntity(branchDTO);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Branch existingBranch = branchRepository.findById(id).orElse(null);
        if (existingBranch == null) {
            return null;
        }
        branchMapper.updateEntityFromDTO(branchDTO, existingBranch);
        Branch updatedBranch = branchRepository.save(existingBranch);
        return branchMapper.toDTO(updatedBranch);
    }

    @Override
    public boolean deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            return false;
        }
        branchRepository.deleteById(id);
        return true;
    }
}