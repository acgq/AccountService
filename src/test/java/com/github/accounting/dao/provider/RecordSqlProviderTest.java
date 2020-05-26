package com.github.accounting.dao.provider;

import com.github.accounting.model.persistence.RecordInPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class RecordSqlProviderTest {

    @Test
    public void testUpdateRecord() {
        RecordSqlProvider provider = new RecordSqlProvider();
        //arrange
        RecordInPersistence record = RecordInPersistence.builder()
                .id(1L)
                .amount(new BigDecimal("188.88"))
                .note("buy cyberpunk2077")
                .build();
        //act
        String sql = provider.updateRecord(record);

        String actualSql = "UPDATE as_record\n" +
                "SET amount = #{amount}, notes = #{notes}\n" +
                "WHERE (id = #{id})";
        Assertions.assertEquals(actualSql, sql);
    }

}