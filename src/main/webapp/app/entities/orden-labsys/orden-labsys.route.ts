import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrdenLabsys } from 'app/shared/model/orden-labsys.model';
import { OrdenLabsysService } from './orden-labsys.service';
import { OrdenLabsysComponent } from './orden-labsys.component';
import { OrdenLabsysDetailComponent } from './orden-labsys-detail.component';
import { OrdenLabsysUpdateComponent } from './orden-labsys-update.component';
import { OrdenLabsysDeletePopupComponent } from './orden-labsys-delete-dialog.component';
import { IOrdenLabsys } from 'app/shared/model/orden-labsys.model';

@Injectable({ providedIn: 'root' })
export class OrdenLabsysResolve implements Resolve<IOrdenLabsys> {
    constructor(private service: OrdenLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orden: HttpResponse<OrdenLabsys>) => orden.body));
        }
        return of(new OrdenLabsys());
    }
}

export const ordenRoute: Routes = [
    {
        path: 'orden',
        component: OrdenLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Ordenes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orden/:id/view',
        component: OrdenLabsysDetailComponent,
        resolve: {
            orden: OrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Detalle Orden'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orden/new',
        component: OrdenLabsysUpdateComponent,
        resolve: {
            orden: OrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nueva Orden'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orden/:id/edit',
        component: OrdenLabsysUpdateComponent,
        resolve: {
            orden: OrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Editar Orden'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordenPopupRoute: Routes = [
    {
        path: 'orden/:id/delete',
        component: OrdenLabsysDeletePopupComponent,
        resolve: {
            orden: OrdenLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eliminar Orden'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
