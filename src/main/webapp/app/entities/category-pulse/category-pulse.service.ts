import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoryPulse } from 'app/shared/model/category-pulse.model';

type EntityResponseType = HttpResponse<ICategoryPulse>;
type EntityArrayResponseType = HttpResponse<ICategoryPulse[]>;

@Injectable({ providedIn: 'root' })
export class CategoryPulseService {
    private resourceUrl = SERVER_API_URL + 'api/categories';

    constructor(private http: HttpClient) {}

    create(category: ICategoryPulse): Observable<EntityResponseType> {
        return this.http.post<ICategoryPulse>(this.resourceUrl, category, { observe: 'response' });
    }

    update(category: ICategoryPulse): Observable<EntityResponseType> {
        return this.http.put<ICategoryPulse>(this.resourceUrl, category, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoryPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoryPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
