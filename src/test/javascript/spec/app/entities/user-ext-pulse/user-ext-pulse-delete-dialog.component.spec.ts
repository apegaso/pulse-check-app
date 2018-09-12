/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { UserExtPulseDeleteDialogComponent } from 'app/entities/user-ext-pulse/user-ext-pulse-delete-dialog.component';
import { UserExtPulseService } from 'app/entities/user-ext-pulse/user-ext-pulse.service';

describe('Component Tests', () => {
    describe('UserExtPulse Management Delete Component', () => {
        let comp: UserExtPulseDeleteDialogComponent;
        let fixture: ComponentFixture<UserExtPulseDeleteDialogComponent>;
        let service: UserExtPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [UserExtPulseDeleteDialogComponent]
            })
                .overrideTemplate(UserExtPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserExtPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserExtPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
