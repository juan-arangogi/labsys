import { NgModule } from '@angular/core';

import { LabsysSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [LabsysSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [LabsysSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class LabsysSharedCommonModule {}
