import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    ProcedimientoLabsysComponent,
    ProcedimientoLabsysDetailComponent,
    ProcedimientoLabsysUpdateComponent,
    ProcedimientoLabsysDeletePopupComponent,
    ProcedimientoLabsysDeleteDialogComponent,
    procedimientoRoute,
    procedimientoPopupRoute
} from './';

const ENTITY_STATES = [...procedimientoRoute, ...procedimientoPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProcedimientoLabsysComponent,
        ProcedimientoLabsysDetailComponent,
        ProcedimientoLabsysUpdateComponent,
        ProcedimientoLabsysDeleteDialogComponent,
        ProcedimientoLabsysDeletePopupComponent
    ],
    entryComponents: [
        ProcedimientoLabsysComponent,
        ProcedimientoLabsysUpdateComponent,
        ProcedimientoLabsysDeleteDialogComponent,
        ProcedimientoLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysProcedimientoLabsysModule {}
