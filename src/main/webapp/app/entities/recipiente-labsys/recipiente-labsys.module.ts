import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    RecipienteLabsysComponent,
    RecipienteLabsysDetailComponent,
    RecipienteLabsysUpdateComponent,
    RecipienteLabsysDeletePopupComponent,
    RecipienteLabsysDeleteDialogComponent,
    recipienteRoute,
    recipientePopupRoute
} from './';

const ENTITY_STATES = [...recipienteRoute, ...recipientePopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipienteLabsysComponent,
        RecipienteLabsysDetailComponent,
        RecipienteLabsysUpdateComponent,
        RecipienteLabsysDeleteDialogComponent,
        RecipienteLabsysDeletePopupComponent
    ],
    entryComponents: [
        RecipienteLabsysComponent,
        RecipienteLabsysUpdateComponent,
        RecipienteLabsysDeleteDialogComponent,
        RecipienteLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysRecipienteLabsysModule {}
