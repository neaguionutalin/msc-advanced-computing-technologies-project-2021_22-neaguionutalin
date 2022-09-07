package com.eldorado.eldoradoservice_vendorgateway.models.enums;

import com.eldorado.eldoradoservice_vendorgateway.models.ExecutionReportBody;
import com.eldorado.eldoradoservice_vendorgateway.models.HeaderDTO;
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
