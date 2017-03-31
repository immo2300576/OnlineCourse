function c =  my_xor(m,k)

% initial c array
min_length  = min(length(m), length(k));
c = [];

m_cut = m(1:min_length);
k_cut = k(1:min_length);



% xor operation
% build-in xor only support zero and non-zero operation

for i = 1:2:min_length
    a = my_str2double(m_cut(i:i+1));
    b = my_str2double(k_cut(i:i+1));
    c_single = bitxor(a,b);
    c = [c dec2hex(c_single,2)];
end


% c = bitxor(double(m_cut),double(k_cut));
% c = dec2hex(c);

end