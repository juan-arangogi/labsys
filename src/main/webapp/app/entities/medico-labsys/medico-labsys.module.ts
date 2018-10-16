import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    MedicoLabsysComponent,
    MedicoLabsysDetailComponent,
    MedicoLabsysUpdateComponent,
    MedicoLabsysDeletePopupComponent,
    MedicoLabsysDeleteDialogComponent,
    medicoRoute,
    medicoPopupRoute
} from './';

const ENTITY_STATES = [...medicoRoute, ...medicoPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MedicoLabsysComponent,
        MedicoLabsysDetailComponent,
        MedicoLabsysUpdateComponent,
        MedicoLabsysDeleteDialogComponent,
        MedicoLabsysDeletePopupComponent
    ],
    entryComponents: [
        MedicoLabsysComponent,
        MedicoLabsysUpdateComponent,
        MedicoLabsysDeleteDialogComponent,
        MedicoLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysMedicoLabsysModule {}
