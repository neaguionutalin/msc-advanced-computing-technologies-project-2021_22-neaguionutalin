package com.eldorado.eldoradosercice_oms.model.enums;

import com.eldorado.eldoradosercice_oms.model.ExecutionReportBody;
import com.eldorado.eldoradosercice_oms.model.HeaderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionReport {
  private HeaderDTO header;
  private ExecutionReportBody body;
}
