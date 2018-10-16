/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { DetalleOrdenLabsysUpdateComponent } from 'app/entities/detalle-orden-labsys/detalle-orden-labsys-update.component';
import { DetalleOrdenLabsysService } from 'app/entities/detalle-orden-labsys/detalle-orden-labsys.service';
import { DetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';

describe('Component Tests', () => {
    describe('DetalleOrdenLabsys Management Update Component', () => {
        let comp: DetalleOrdenLabsysUpdateComponent;
        let fixture: ComponentFixture<DetalleOrdenLabsysUpdateComponent>;
        let service: DetalleOrdenLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [DetalleOrdenLabsysUpdateComponent]
            })
                .overrideTemplate(DetalleOrdenLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetalleOrdenLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleOrdenLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DetalleOrdenLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detalleOrden = entity;
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
                    const entity = new DetalleOrdenLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detalleOrden = entity;
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
