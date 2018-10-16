import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    OrdenLabsysComponent,
    OrdenLabsysDetailComponent,
    OrdenLabsysUpdateComponent,
    OrdenLabsysDeletePopupComponent,
    OrdenLabsysDeleteDialogComponent,
    ordenRoute,
    ordenPopupRoute
} from './';

const ENTITY_STATES = [...ordenRoute, ...ordenPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrdenLabsysComponent,
        OrdenLabsysDetailComponent,
        OrdenLabsysUpdateComponent,
        OrdenLabsysDeleteDialogComponent,
        OrdenLabsysDeletePopupComponent
    ],
    entryComponents: [OrdenLabsysComponent, OrdenLabsysUpdateComponent, OrdenLabsysDeleteDialogComponent, OrdenLabsysDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysOrdenLabsysModule {}
