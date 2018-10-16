import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';

type EntityResponseType = HttpResponse<IProcedimientoLabsys>;
type EntityArrayResponseType = HttpResponse<IProcedimientoLabsys[]>;

@Injectable({ providedIn: 'root' })
export class ProcedimientoLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/procedimientos';

    constructor(private http: HttpClient) {}

    create(procedimiento: IProcedimientoLabsys): Observable<EntityResponseType> {
        return this.http.post<IProcedimientoLabsys>(this.resourceUrl, procedimiento, { observe: 'response' });
    }

    update(procedimiento: IProcedimientoLabsys): Observable<EntityResponseType> {
        return this.http.put<IProcedimientoLabsys>(this.resourceUrl, procedimiento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProcedimientoLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProcedimientoLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
