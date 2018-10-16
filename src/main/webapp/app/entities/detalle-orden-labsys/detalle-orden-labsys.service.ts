import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';

type EntityResponseType = HttpResponse<IDetalleOrdenLabsys>;
type EntityArrayResponseType = HttpResponse<IDetalleOrdenLabsys[]>;

@Injectable({ providedIn: 'root' })
export class DetalleOrdenLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/detalle-ordens';

    constructor(private http: HttpClient) {}

    create(detalleOrden: IDetalleOrdenLabsys): Observable<EntityResponseType> {
        return this.http.post<IDetalleOrdenLabsys>(this.resourceUrl, detalleOrden, { observe: 'response' });
    }

    update(detalleOrden: IDetalleOrdenLabsys): Observable<EntityResponseType> {
        return this.http.put<IDetalleOrdenLabsys>(this.resourceUrl, detalleOrden, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDetalleOrdenLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleOrdenLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
