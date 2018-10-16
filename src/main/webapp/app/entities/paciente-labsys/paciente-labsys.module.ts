import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    PacienteLabsysComponent,
    PacienteLabsysDetailComponent,
    PacienteLabsysUpdateComponent,
    PacienteLabsysDeletePopupComponent,
    PacienteLabsysDeleteDialogComponent,
    pacienteRoute,
    pacientePopupRoute
} from './';

const ENTITY_STATES = [...pacienteRoute, ...pacientePopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PacienteLabsysComponent,
        PacienteLabsysDetailComponent,
        PacienteLabsysUpdateComponent,
        PacienteLabsysDeleteDialogComponent,
        PacienteLabsysDeletePopupComponent
    ],
    entryComponents: [
        PacienteLabsysComponent,
        PacienteLabsysUpdateComponent,
        PacienteLabsysDeleteDialogComponent,
        PacienteLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysPacienteLabsysModule {}
