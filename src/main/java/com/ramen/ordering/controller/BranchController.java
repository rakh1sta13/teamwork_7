package com.ramen.ordering.controller;

import com.ramen.ordering.dto.BranchDTO;
import com.ramen.ordering.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branch Management", description = "Endpoints for managing ramen restaurant branches")
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    @Operation(summary = "Get all branches", description = "Retrieve a list of all branches")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        List<BranchDTO> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active branches", description = "Retrieve a list of all active branches")
    public ResponseEntity<List<BranchDTO>> getActiveBranches() {
        List<BranchDTO> branches = branchService.getActiveBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID", description = "Retrieve a specific branch by its ID")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        BranchDTO branch = branchService.getBranchById(id);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new branch", description = "Create a new branch")
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a branch", description = "Update an existing branch by its ID")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        BranchDTO updatedBranch = branchService.updateBranch(id, branchDTO);
        return updatedBranch != null ? ResponseEntity.ok(updatedBranch) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a branch", description = "Delete a branch by its ID")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        boolean deleted = branchService.deleteBranch(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}