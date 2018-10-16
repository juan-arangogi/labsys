import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';
import { DetalleOrdenLabsysService } from './detalle-orden-labsys.service';
import { DetalleOrdenLabsysComponent } from './detalle-orden-labsys.component';
import { DetalleOrdenLabsysDetailComponent } from './detalle-orden-labsys-detail.component';
import { DetalleOrdenLabsysUpdateComponent } from './detalle-orden-labsys-update.component';
import { DetalleOrdenLabsysDeletePopupComponent } from './detalle-orden-labsys-delete-dialog.component';
import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';

@Injectable({ providedIn: 'root' })
export class DetalleOrdenLabsysResolve implements Resolve<IDetalleOrdenLabsys> {
    constructor(private service: DetalleOrdenLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((detalleOrden: HttpResponse<DetalleOrdenLabsys>) => detalleOrden.body));
        }
        return of(new DetalleOrdenLabsys());
    }
}

export const detalleOrdenRoute: Routes = [
    {
        path: 'detalle-orden',
        component: DetalleOrdenLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DetalleOrdens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-orden/:id/view',
        component: DetalleOrdenLabsysDetailComponent,
        resolve: {
            detalleOrden: DetalleOrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetalleOrdens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-orden/new',
        component: DetalleOrdenLabsysUpdateComponent,
        resolve: {
            detalleOrden: DetalleOrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetalleOrdens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-orden/:id/edit',
        component: DetalleOrdenLabsysUpdateComponent,
        resolve: {
            detalleOrden: DetalleOrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetalleOrdens'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detalleOrdenPopupRoute: Routes = [
    {
        path: 'detalle-orden/:id/delete',
        component: DetalleOrdenLabsysDeletePopupComponent,
        resolve: {
            detalleOrden: DetalleOrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DetalleOrdens'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
