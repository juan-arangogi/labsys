import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';

type EntityResponseType = HttpResponse<IEntidadSaludLabsys>;
type EntityArrayResponseType = HttpResponse<IEntidadSaludLabsys[]>;

@Injectable({ providedIn: 'root' })
export class EntidadSaludLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/entidad-saluds';

    constructor(private http: HttpClient) {}

    create(entidadSalud: IEntidadSaludLabsys): Observable<EntityResponseType> {
        return this.http.post<IEntidadSaludLabsys>(this.resourceUrl, entidadSalud, { observe: 'response' });
    }

    update(entidadSalud: IEntidadSaludLabsys): Observable<EntityResponseType> {
        return this.http.put<IEntidadSaludLabsys>(this.resourceUrl, entidadSalud, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEntidadSaludLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEntidadSaludLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
