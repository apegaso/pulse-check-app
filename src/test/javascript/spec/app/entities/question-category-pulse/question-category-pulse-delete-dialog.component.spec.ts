/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { QuestionCategoryPulseDeleteDialogComponent } from 'app/entities/question-category-pulse/question-category-pulse-delete-dialog.component';
import { QuestionCategoryPulseService } from 'app/entities/question-category-pulse/question-category-pulse.service';

describe('Component Tests', () => {
    describe('QuestionCategoryPulse Management Delete Component', () => {
        let comp: QuestionCategoryPulseDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionCategoryPulseDeleteDialogComponent>;
        let service: QuestionCategoryPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [QuestionCategoryPulseDeleteDialogComponent]
            })
                .overrideTemplate(QuestionCategoryPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionCategoryPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionCategoryPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
