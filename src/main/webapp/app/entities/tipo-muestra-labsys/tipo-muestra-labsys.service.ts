import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';

type EntityResponseType = HttpResponse<ITipoMuestraLabsys>;
type EntityArrayResponseType = HttpResponse<ITipoMuestraLabsys[]>;

@Injectable({ providedIn: 'root' })
export class TipoMuestraLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/tipo-muestras';

    constructor(private http: HttpClient) {}

    create(tipoMuestra: ITipoMuestraLabsys): Observable<EntityResponseType> {
        return this.http.post<ITipoMuestraLabsys>(this.resourceUrl, tipoMuestra, { observe: 'response' });
    }

    update(tipoMuestra: ITipoMuestraLabsys): Observable<EntityResponseType> {
        return this.http.put<ITipoMuestraLabsys>(this.resourceUrl, tipoMuestra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoMuestraLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoMuestraLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
