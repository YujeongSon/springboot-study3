package com.zerobase.lms.admin.mapper;

import com.zerobase.lms.admin.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> selectList(MemberDto memberDto);
}