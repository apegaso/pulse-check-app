/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionCategoryPulseDetailComponent } from 'app/entities/question-category-pulse/question-category-pulse-detail.component';
import { QuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

describe('Component Tests', () => {
    describe('QuestionCategoryPulse Management Detail Component', () => {
        let comp: QuestionCategoryPulseDetailComponent;
        let fixture: ComponentFixture<QuestionCategoryPulseDetailComponent>;
        const route = ({ data: of({ questionCategory: new QuestionCategoryPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionCategoryPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionCategoryPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionCategoryPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
