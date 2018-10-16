import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    EntidadSaludLabsysComponent,
    EntidadSaludLabsysDetailComponent,
    EntidadSaludLabsysUpdateComponent,
    EntidadSaludLabsysDeletePopupComponent,
    EntidadSaludLabsysDeleteDialogComponent,
    entidadSaludRoute,
    entidadSaludPopupRoute
} from './';

const ENTITY_STATES = [...entidadSaludRoute, ...entidadSaludPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EntidadSaludLabsysComponent,
        EntidadSaludLabsysDetailComponent,
        EntidadSaludLabsysUpdateComponent,
        EntidadSaludLabsysDeleteDialogComponent,
        EntidadSaludLabsysDeletePopupComponent
    ],
    entryComponents: [
        EntidadSaludLabsysComponent,
        EntidadSaludLabsysUpdateComponent,
        EntidadSaludLabsysDeleteDialogComponent,
        EntidadSaludLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysEntidadSaludLabsysModule {}
