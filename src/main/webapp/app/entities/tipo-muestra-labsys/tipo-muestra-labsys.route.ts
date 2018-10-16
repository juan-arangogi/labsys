import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';
import { TipoMuestraLabsysService } from './tipo-muestra-labsys.service';
import { TipoMuestraLabsysComponent } from './tipo-muestra-labsys.component';
import { TipoMuestraLabsysDetailComponent } from './tipo-muestra-labsys-detail.component';
import { TipoMuestraLabsysUpdateComponent } from './tipo-muestra-labsys-update.component';
import { TipoMuestraLabsysDeletePopupComponent } from './tipo-muestra-labsys-delete-dialog.component';
import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';

@Injectable({ providedIn: 'root' })
export class TipoMuestraLabsysResolve implements Resolve<ITipoMuestraLabsys> {
    constructor(private service: TipoMuestraLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tipoMuestra: HttpResponse<TipoMuestraLabsys>) => tipoMuestra.body));
        }
        return of(new TipoMuestraLabsys());
    }
}

export const tipoMuestraRoute: Routes = [
    {
        path: 'tipo-muestra',
        component: TipoMuestraLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TipoMuestras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-muestra/:id/view',
        component: TipoMuestraLabsysDetailComponent,
        resolve: {
            tipoMuestra: TipoMuestraLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoMuestras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-muestra/new',
        component: TipoMuestraLabsysUpdateComponent,
        resolve: {
            tipoMuestra: TipoMuestraLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoMuestras'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-muestra/:id/edit',
        component: TipoMuestraLabsysUpdateComponent,
        resolve: {
            tipoMuestra: TipoMuestraLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoMuestras'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoMuestraPopupRoute: Routes = [
    {
        path: 'tipo-muestra/:id/delete',
        component: TipoMuestraLabsysDeletePopupComponent,
        resolve: {
            tipoMuestra: TipoMuestraLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoMuestras'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
