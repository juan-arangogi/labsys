import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    DetalleOrdenLabsysComponent,
    DetalleOrdenLabsysDetailComponent,
    DetalleOrdenLabsysUpdateComponent,
    DetalleOrdenLabsysDeletePopupComponent,
    DetalleOrdenLabsysDeleteDialogComponent,
    detalleOrdenRoute,
    detalleOrdenPopupRoute
} from './';

const ENTITY_STATES = [...detalleOrdenRoute, ...detalleOrdenPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DetalleOrdenLabsysComponent,
        DetalleOrdenLabsysDetailComponent,
        DetalleOrdenLabsysUpdateComponent,
        DetalleOrdenLabsysDeleteDialogComponent,
        DetalleOrdenLabsysDeletePopupComponent
    ],
    entryComponents: [
        DetalleOrdenLabsysComponent,
        DetalleOrdenLabsysUpdateComponent,
        DetalleOrdenLabsysDeleteDialogComponent,
        DetalleOrdenLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysDetalleOrdenLabsysModule {}
