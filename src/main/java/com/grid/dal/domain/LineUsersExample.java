package com.grid.dal.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LineUsersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LineUsersExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Short value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Short value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Short value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Short value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Short value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Short value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Short> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Short> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Short value1, Short value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Short value1, Short value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInspectorIsNull() {
            addCriterion("INSPECTOR is null");
            return (Criteria) this;
        }

        public Criteria andInspectorIsNotNull() {
            addCriterion("INSPECTOR is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorEqualTo(Short value) {
            addCriterion("INSPECTOR =", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorNotEqualTo(Short value) {
            addCriterion("INSPECTOR <>", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorGreaterThan(Short value) {
            addCriterion("INSPECTOR >", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorGreaterThanOrEqualTo(Short value) {
            addCriterion("INSPECTOR >=", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorLessThan(Short value) {
            addCriterion("INSPECTOR <", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorLessThanOrEqualTo(Short value) {
            addCriterion("INSPECTOR <=", value, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorIn(List<Short> values) {
            addCriterion("INSPECTOR in", values, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorNotIn(List<Short> values) {
            addCriterion("INSPECTOR not in", values, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorBetween(Short value1, Short value2) {
            addCriterion("INSPECTOR between", value1, value2, "inspector");
            return (Criteria) this;
        }

        public Criteria andInspectorNotBetween(Short value1, Short value2) {
            addCriterion("INSPECTOR not between", value1, value2, "inspector");
            return (Criteria) this;
        }

        public Criteria andInsnameIsNull() {
            addCriterion("INSNAME is null");
            return (Criteria) this;
        }

        public Criteria andInsnameIsNotNull() {
            addCriterion("INSNAME is not null");
            return (Criteria) this;
        }

        public Criteria andInsnameEqualTo(String value) {
            addCriterion("INSNAME =", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameNotEqualTo(String value) {
            addCriterion("INSNAME <>", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameGreaterThan(String value) {
            addCriterion("INSNAME >", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameGreaterThanOrEqualTo(String value) {
            addCriterion("INSNAME >=", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameLessThan(String value) {
            addCriterion("INSNAME <", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameLessThanOrEqualTo(String value) {
            addCriterion("INSNAME <=", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameLike(String value) {
            addCriterion("INSNAME like", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameNotLike(String value) {
            addCriterion("INSNAME not like", value, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameIn(List<String> values) {
            addCriterion("INSNAME in", values, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameNotIn(List<String> values) {
            addCriterion("INSNAME not in", values, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameBetween(String value1, String value2) {
            addCriterion("INSNAME between", value1, value2, "insname");
            return (Criteria) this;
        }

        public Criteria andInsnameNotBetween(String value1, String value2) {
            addCriterion("INSNAME not between", value1, value2, "insname");
            return (Criteria) this;
        }

        public Criteria andLineIsNull() {
            addCriterion("LINE is null");
            return (Criteria) this;
        }

        public Criteria andLineIsNotNull() {
            addCriterion("LINE is not null");
            return (Criteria) this;
        }

        public Criteria andLineEqualTo(Long value) {
            addCriterion("LINE =", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotEqualTo(Long value) {
            addCriterion("LINE <>", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThan(Long value) {
            addCriterion("LINE >", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThanOrEqualTo(Long value) {
            addCriterion("LINE >=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThan(Long value) {
            addCriterion("LINE <", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThanOrEqualTo(Long value) {
            addCriterion("LINE <=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineIn(List<Long> values) {
            addCriterion("LINE in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotIn(List<Long> values) {
            addCriterion("LINE not in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineBetween(Long value1, Long value2) {
            addCriterion("LINE between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotBetween(Long value1, Long value2) {
            addCriterion("LINE not between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andLinenameIsNull() {
            addCriterion("LINENAME is null");
            return (Criteria) this;
        }

        public Criteria andLinenameIsNotNull() {
            addCriterion("LINENAME is not null");
            return (Criteria) this;
        }

        public Criteria andLinenameEqualTo(String value) {
            addCriterion("LINENAME =", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameNotEqualTo(String value) {
            addCriterion("LINENAME <>", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameGreaterThan(String value) {
            addCriterion("LINENAME >", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameGreaterThanOrEqualTo(String value) {
            addCriterion("LINENAME >=", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameLessThan(String value) {
            addCriterion("LINENAME <", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameLessThanOrEqualTo(String value) {
            addCriterion("LINENAME <=", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameLike(String value) {
            addCriterion("LINENAME like", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameNotLike(String value) {
            addCriterion("LINENAME not like", value, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameIn(List<String> values) {
            addCriterion("LINENAME in", values, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameNotIn(List<String> values) {
            addCriterion("LINENAME not in", values, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameBetween(String value1, String value2) {
            addCriterion("LINENAME between", value1, value2, "linename");
            return (Criteria) this;
        }

        public Criteria andLinenameNotBetween(String value1, String value2) {
            addCriterion("LINENAME not between", value1, value2, "linename");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNull() {
            addCriterion("DISTANCE is null");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNotNull() {
            addCriterion("DISTANCE is not null");
            return (Criteria) this;
        }

        public Criteria andDistanceEqualTo(BigDecimal value) {
            addCriterion("DISTANCE =", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotEqualTo(BigDecimal value) {
            addCriterion("DISTANCE <>", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThan(BigDecimal value) {
            addCriterion("DISTANCE >", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DISTANCE >=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThan(BigDecimal value) {
            addCriterion("DISTANCE <", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DISTANCE <=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceIn(List<BigDecimal> values) {
            addCriterion("DISTANCE in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotIn(List<BigDecimal> values) {
            addCriterion("DISTANCE not in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISTANCE between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DISTANCE not between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andInsideIsNull() {
            addCriterion("INSIDE is null");
            return (Criteria) this;
        }

        public Criteria andInsideIsNotNull() {
            addCriterion("INSIDE is not null");
            return (Criteria) this;
        }

        public Criteria andInsideEqualTo(Short value) {
            addCriterion("INSIDE =", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideNotEqualTo(Short value) {
            addCriterion("INSIDE <>", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideGreaterThan(Short value) {
            addCriterion("INSIDE >", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideGreaterThanOrEqualTo(Short value) {
            addCriterion("INSIDE >=", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideLessThan(Short value) {
            addCriterion("INSIDE <", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideLessThanOrEqualTo(Short value) {
            addCriterion("INSIDE <=", value, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideIn(List<Short> values) {
            addCriterion("INSIDE in", values, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideNotIn(List<Short> values) {
            addCriterion("INSIDE not in", values, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideBetween(Short value1, Short value2) {
            addCriterion("INSIDE between", value1, value2, "inside");
            return (Criteria) this;
        }

        public Criteria andInsideNotBetween(Short value1, Short value2) {
            addCriterion("INSIDE not between", value1, value2, "inside");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}