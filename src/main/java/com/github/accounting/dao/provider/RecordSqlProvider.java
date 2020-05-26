package com.github.accounting.dao.provider;

import com.github.accounting.model.persistence.RecordInPersistence;

import org.apache.ibatis.jdbc.SQL;

public class RecordSqlProvider {

    /**
     * Generate dynamic sql .
     *
     * @param record record to update.
     * @return sql string.
     */
    public String updateRecord(RecordInPersistence record) {
        return new SQL() {
            {
                UPDATE("as_record");
                if (record.getAmount() != null) {
                    SET("amount = #{amount}");
                }
                if (record.getUserId() != null) {
                    SET("user_id = #{userId}");
                }
                if (record.getNote() != null) {
                    SET("notes = #{notes}");
                }
                if (record.getCategory() != null) {
                    SET("category = #{category}");
                }
                if (record.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (record.getCreateTime() != null) {
                    SET("create_time = #{createTime}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
