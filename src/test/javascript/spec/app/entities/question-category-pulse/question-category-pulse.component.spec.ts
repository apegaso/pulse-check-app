/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionCategoryPulseComponent } from 'app/entities/question-category-pulse/question-category-pulse.component';
import { QuestionCategoryPulseService } from 'app/entities/question-category-pulse/question-category-pulse.service';
import { QuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

describe('Component Tests', () => {
    describe('QuestionCategoryPulse Management Component', () => {
        let comp: QuestionCategoryPulseComponent;
        let fixture: ComponentFixture<QuestionCategoryPulseComponent>;
        let service: QuestionCategoryPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionCategoryPulseComponent],
                providers: []
            })
                .overrideTemplate(QuestionCategoryPulseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionCategoryPulseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionCategoryPulseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new QuestionCategoryPulse(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.questionCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
