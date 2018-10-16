import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';
import { EntidadSaludLabsysService } from './entidad-salud-labsys.service';
import { EntidadSaludLabsysComponent } from './entidad-salud-labsys.component';
import { EntidadSaludLabsysDetailComponent } from './entidad-salud-labsys-detail.component';
import { EntidadSaludLabsysUpdateComponent } from './entidad-salud-labsys-update.component';
import { EntidadSaludLabsysDeletePopupComponent } from './entidad-salud-labsys-delete-dialog.component';
import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';

@Injectable({ providedIn: 'root' })
export class EntidadSaludLabsysResolve implements Resolve<IEntidadSaludLabsys> {
    constructor(private service: EntidadSaludLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((entidadSalud: HttpResponse<EntidadSaludLabsys>) => entidadSalud.body));
        }
        return of(new EntidadSaludLabsys());
    }
}

export const entidadSaludRoute: Routes = [
    {
        path: 'entidad-salud',
        component: EntidadSaludLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'EntidadSaluds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entidad-salud/:id/view',
        component: EntidadSaludLabsysDetailComponent,
        resolve: {
            entidadSalud: EntidadSaludLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntidadSaluds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entidad-salud/new',
        component: EntidadSaludLabsysUpdateComponent,
        resolve: {
            entidadSalud: EntidadSaludLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntidadSaluds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entidad-salud/:id/edit',
        component: EntidadSaludLabsysUpdateComponent,
        resolve: {
            entidadSalud: EntidadSaludLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntidadSaluds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entidadSaludPopupRoute: Routes = [
    {
        path: 'entidad-salud/:id/delete',
        component: EntidadSaludLabsysDeletePopupComponent,
        resolve: {
            entidadSalud: EntidadSaludLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntidadSaluds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
