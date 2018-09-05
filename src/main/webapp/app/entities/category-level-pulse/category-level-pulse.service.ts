import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';

type EntityResponseType = HttpResponse<ICategoryLevelPulse>;
type EntityArrayResponseType = HttpResponse<ICategoryLevelPulse[]>;

@Injectable({ providedIn: 'root' })
export class CategoryLevelPulseService {
    private resourceUrl = SERVER_API_URL + 'api/category-levels';

    constructor(private http: HttpClient) {}

    create(categoryLevel: ICategoryLevelPulse): Observable<EntityResponseType> {
        return this.http.post<ICategoryLevelPulse>(this.resourceUrl, categoryLevel, { observe: 'response' });
    }

    update(categoryLevel: ICategoryLevelPulse): Observable<EntityResponseType> {
        return this.http.put<ICategoryLevelPulse>(this.resourceUrl, categoryLevel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoryLevelPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoryLevelPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
