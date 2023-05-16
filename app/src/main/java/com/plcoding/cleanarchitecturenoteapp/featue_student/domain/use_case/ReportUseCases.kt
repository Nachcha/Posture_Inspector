package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

data class ReportUseCases(
    val addReport: AddReport,
    val getReportsByType: GetReportsByType,
    val updateReport: UpdateReport,
    val deleteReport: DeleteReport
)