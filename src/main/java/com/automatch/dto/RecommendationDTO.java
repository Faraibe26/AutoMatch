package com.automatch.dto;

/**
 * RecommendationDTO for API responses containing recommendation details.
 */
public class RecommendationDTO {
    
    private Long id;
    private CarDTO car;
    private Integer matchScore;
    private Integer budgetScore;
    private Integer mpgScore;
    private Integer reliabilityScore;
    private Integer maintenanceScore;
    private Integer mileageScore;
    private String explanation;
    private Integer rank;
    private String tierLabel;
    private String summary;

    public RecommendationDTO() {}

    public RecommendationDTO(Long id, CarDTO car, Integer matchScore, Integer budgetScore, 
                            Integer mpgScore, Integer reliabilityScore, Integer maintenanceScore, 
                            Integer mileageScore, String explanation, Integer rank, String tierLabel, String summary) {
        this.id = id;
        this.car = car;
        this.matchScore = matchScore;
        this.budgetScore = budgetScore;
        this.mpgScore = mpgScore;
        this.reliabilityScore = reliabilityScore;
        this.maintenanceScore = maintenanceScore;
        this.mileageScore = mileageScore;
        this.explanation = explanation;
        this.rank = rank;
        this.tierLabel = tierLabel;
        this.summary = summary;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CarDTO getCar() { return car; }
    public void setCar(CarDTO car) { this.car = car; }

    public Integer getMatchScore() { return matchScore; }
    public void setMatchScore(Integer matchScore) { this.matchScore = matchScore; }

    public Integer getBudgetScore() { return budgetScore; }
    public void setBudgetScore(Integer budgetScore) { this.budgetScore = budgetScore; }

    public Integer getMpgScore() { return mpgScore; }
    public void setMpgScore(Integer mpgScore) { this.mpgScore = mpgScore; }

    public Integer getReliabilityScore() { return reliabilityScore; }
    public void setReliabilityScore(Integer reliabilityScore) { this.reliabilityScore = reliabilityScore; }

    public Integer getMaintenanceScore() { return maintenanceScore; }
    public void setMaintenanceScore(Integer maintenanceScore) { this.maintenanceScore = maintenanceScore; }

    public Integer getMileageScore() { return mileageScore; }
    public void setMileageScore(Integer mileageScore) { this.mileageScore = mileageScore; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public String getTierLabel() { return tierLabel; }
    public void setTierLabel(String tierLabel) { this.tierLabel = tierLabel; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public static RecommendationDTOBuilder builder() {
        return new RecommendationDTOBuilder();
    }

    public static class RecommendationDTOBuilder {
        private Long id;
        private CarDTO car;
        private Integer matchScore;
        private Integer budgetScore;
        private Integer mpgScore;
        private Integer reliabilityScore;
        private Integer maintenanceScore;
        private Integer mileageScore;
        private String explanation;
        private Integer rank;
        private String tierLabel;
        private String summary;

        public RecommendationDTOBuilder id(Long id) { this.id = id; return this; }
        public RecommendationDTOBuilder car(CarDTO car) { this.car = car; return this; }
        public RecommendationDTOBuilder matchScore(Integer matchScore) { this.matchScore = matchScore; return this; }
        public RecommendationDTOBuilder budgetScore(Integer budgetScore) { this.budgetScore = budgetScore; return this; }
        public RecommendationDTOBuilder mpgScore(Integer mpgScore) { this.mpgScore = mpgScore; return this; }
        public RecommendationDTOBuilder reliabilityScore(Integer reliabilityScore) { this.reliabilityScore = reliabilityScore; return this; }
        public RecommendationDTOBuilder maintenanceScore(Integer maintenanceScore) { this.maintenanceScore = maintenanceScore; return this; }
        public RecommendationDTOBuilder mileageScore(Integer mileageScore) { this.mileageScore = mileageScore; return this; }
        public RecommendationDTOBuilder explanation(String explanation) { this.explanation = explanation; return this; }
        public RecommendationDTOBuilder rank(Integer rank) { this.rank = rank; return this; }
        public RecommendationDTOBuilder tierLabel(String tierLabel) { this.tierLabel = tierLabel; return this; }
        public RecommendationDTOBuilder summary(String summary) { this.summary = summary; return this; }

        public RecommendationDTO build() {
            return new RecommendationDTO(id, car, matchScore, budgetScore, mpgScore, reliabilityScore, 
                                        maintenanceScore, mileageScore, explanation, rank, tierLabel, summary);
        }
    }
}
