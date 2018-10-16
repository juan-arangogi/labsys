import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';
import { ProcedimientoLabsysService } from './procedimiento-labsys.service';
import { ProcedimientoLabsysComponent } from './procedimiento-labsys.component';
import { ProcedimientoLabsysDetailComponent } from './procedimiento-labsys-detail.component';
import { ProcedimientoLabsysUpdateComponent } from './procedimiento-labsys-update.component';
import { ProcedimientoLabsysDeletePopupComponent } from './procedimiento-labsys-delete-dialog.component';
import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';

@Injectable({ providedIn: 'root' })
export class ProcedimientoLabsysResolve implements Resolve<IProcedimientoLabsys> {
    constructor(private service: ProcedimientoLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((procedimiento: HttpResponse<ProcedimientoLabsys>) => procedimiento.body));
        }
        return of(new ProcedimientoLabsys());
    }
}

export const procedimientoRoute: Routes = [
    {
        path: 'procedimiento',
        component: ProcedimientoLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Procedimientos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'procedimiento/:id/view',
        component: ProcedimientoLabsysDetailComponent,
        resolve: {
            procedimiento: ProcedimientoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedimientos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'procedimiento/new',
        component: ProcedimientoLabsysUpdateComponent,
        resolve: {
            procedimiento: ProcedimientoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedimientos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'procedimiento/:id/edit',
        component: ProcedimientoLabsysUpdateComponent,
        resolve: {
            procedimiento: ProcedimientoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedimientos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const procedimientoPopupRoute: Routes = [
    {
        path: 'procedimiento/:id/delete',
        component: ProcedimientoLabsysDeletePopupComponent,
        resolve: {
            procedimiento: ProcedimientoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Procedimientos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
