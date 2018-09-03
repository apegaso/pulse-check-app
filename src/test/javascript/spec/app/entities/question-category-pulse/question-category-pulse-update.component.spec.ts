/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionCategoryPulseUpdateComponent } from 'app/entities/question-category-pulse/question-category-pulse-update.component';
import { QuestionCategoryPulseService } from 'app/entities/question-category-pulse/question-category-pulse.service';
import { QuestionCategoryPulse } from 'app/shared/model/question-category-pulse.model';

describe('Component Tests', () => {
    describe('QuestionCategoryPulse Management Update Component', () => {
        let comp: QuestionCategoryPulseUpdateComponent;
        let fixture: ComponentFixture<QuestionCategoryPulseUpdateComponent>;
        let service: QuestionCategoryPulseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionCategoryPulseUpdateComponent]
            })
                .overrideTemplate(QuestionCategoryPulseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionCategoryPulseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionCategoryPulseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionCategoryPulse(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionCategory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionCategoryPulse();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionCategory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
