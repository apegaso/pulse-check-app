/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryLevelPulseComponent } from 'app/entities/category-level-pulse/category-level-pulse.component';
import { CategoryLevelPulseService } from 'app/entities/category-level-pulse/category-level-pulse.service';
import { CategoryLevelPulse } from 'app/shared/model/category-level-pulse.model';

describe('Component Tests', () => {
    describe('CategoryLevelPulse Management Component', () => {
        let comp: CategoryLevelPulseComponent;
        let fixture: ComponentFixture<CategoryLevelPulseComponent>;
        let service: CategoryLevelPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryLevelPulseComponent],
                providers: []
            })
                .overrideTemplate(CategoryLevelPulseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoryLevelPulseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryLevelPulseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CategoryLevelPulse(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoryLevels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
