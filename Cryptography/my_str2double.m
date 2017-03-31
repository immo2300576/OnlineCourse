function value = my_str2double(str)

if length(str) ~= 2
    value = 0;
else
    buf = double(str);
    
    for i = 1:2
        if buf(i) > 96
            buf(i) = buf(i)-97 + 10; % 'abcdef'
        else if buf(i) > 47
                buf(i) = buf(i) - 48; % 0123456789
            end
        end
    end
    value = buf(1)*16+buf(2);
end

end