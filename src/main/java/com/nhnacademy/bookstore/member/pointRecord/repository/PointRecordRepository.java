package com.nhnacademy.bookstore.member.pointRecord.repository;

import com.nhnacademy.bookstore.entity.pointRecord.PointRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
}
